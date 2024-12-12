from flask import Blueprint, json, render_template, request, redirect, flash, jsonify, session
import requests

routeOrder = Blueprint("routeOrder", __name__)

@routeOrder.route('/vehicle/list/<int:idVehiculo>', methods=['GET'])
def list_vehicles(idVehiculo):
    print(f"ID: {idVehiculo}")
    
    try:
        r = requests.get(f"http://localhost:8086/api/Order/list/{idVehiculo}")
        r.raise_for_status()  
        data = r.json()
        print(data) 
        vehicles = data["data"]
        return render_template('/vehiculo/lista.html', lista=data["data"], idVehiculo=idVehiculo)
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error occurred: {http_err}")
        return jsonify({"error": "Error en la respuesta del servidor"}), r.status_code
    except Exception as err:
        print(f"Other error occurred: {err}")	
        return jsonify({"error": "Error en la respuesta del servidor"}), 500

@routeOrder.route('/vehicle/save', methods=['POST'])
def save_vehicle():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    idVehiculo = session.get('idVehiculo') 
    dataF = {
        "idPersona": idVehiculo,
        "marca": form["marca"],
        "modelo": form["modelo"],
        "placa": form["placa"],
        "color": form["color"],
        "descripcion": form["descripcion"]
    }
    print("Data to send:", dataF)  
    r = requests.post("http://localhost:8086/api/vehicle/save", data=json.dumps(dataF), headers=headers)
    print("Response status code:", r.status_code)  
    print("Response data:", r.text)  
    if r.status_code == 200:
        flash('Vehículo guardado correctamente', category='info')
        return redirect('/vehicle/list')
    else:
        flash('Error al guardar vehículo', category='error')
        return redirect('/vehicle/list')

@routeOrder.route('/vehicle/register/<int:idVehiculo>', methods=['GET'])
def register_vehicle(idVehiculo):
    r = requests.get("http://localhost:8086/api/person/list")
    data = r.json()
    session['idVehiculo'] = idVehiculo  
    return render_template('/vehiculo/guardar.html', lista=data["data"], idVehiculo=idVehiculo)