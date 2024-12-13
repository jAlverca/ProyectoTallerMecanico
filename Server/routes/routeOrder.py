from flask import Blueprint, json, render_template, request, redirect, flash, jsonify, session
import requests

routeOrder = Blueprint("routeOrder", __name__)

@routeOrder.route('/order/list/<int:idVehiculo>', methods=['GET'])
def list_vehicles(idVehiculo):
    try:
        r = requests.get(f"http://localhost:8086/api/order/list/{idVehiculo}")
        r.raise_for_status()  
        data = r.json()
        return render_template('/order/lista.html', lista=data["data"], idVehiculo=idVehiculo)
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error occurred: {http_err}")
        return jsonify({"error": "Error en la respuesta del servidor"}), r.status_code
    except Exception as err:
        print(f"Other error occurred: {err}")	
        return jsonify({"error": "Error en la respuesta del servidor"}), 500

@routeOrder.route('/order/save', methods=['POST'])
def save_order():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    idVehiculo = session.get('idVehiculo') 
    dataF = {
        "idVehiculo": idVehiculo,
        "fecha": form["fecha"],
        "subtotal": form["subtotal"],
        "iva": form["iva"],
        "total": form["total"],
        "nroOrder": form["nroOrder"],
        "idTecnico": form["idTecnico"]
    }
    r = requests.post("http://localhost:8086/api/order/save", data=json.dumps(dataF), headers=headers)
    if r.status_code == 200:
        flash('Vehículo guardado correctamente', category='info')
        return redirect('/order/list')
    else:
        flash('Error al guardar vehículo', category='error')
        return redirect('/order/list')

@routeOrder.route('/order/register/<int:idVehiculo>', methods=['GET'])
def register_vehicle(idVehiculo):
    r = requests.get("http://localhost:8086/api/order/list")
    data = r.json()
    session['idVehiculo'] = idVehiculo  
    return render_template('/order/guardar.html', lista=data["data"], idVehiculo=idVehiculo)