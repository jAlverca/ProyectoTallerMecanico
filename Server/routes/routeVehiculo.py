from flask import Blueprint, json, render_template, request, redirect, flash, jsonify, session
import requests

routeVehiculo = Blueprint("routeVehiculo", __name__)

@routeVehiculo.route('/vehicle/list/<int:person_id>', methods=['GET'])
def list_vehicles(person_id):
    try:
        r = requests.get(f"http://localhost:8086/api/vehicle/list/{person_id}")
        r.raise_for_status()  
        data = r.json()
        return render_template('/vehiculo/lista.html', lista=data["data"], person_id=person_id)
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error occurred: {http_err}")
        return jsonify({"error": "Error en la respuesta del servidor"}), r.status_code
    except Exception as err:
        print(f"Other error occurred: {err}")	
        return jsonify({"error": "Error en la respuesta del servidor"}), 500

@routeVehiculo.route('/vehicle/save', methods=['POST'])
def save_vehicle():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    person_id = session.get('person_id') 
    dataF = {
        "idPersona": person_id,
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

@routeVehiculo.route('/vehicle/register/<int:person_id>', methods=['GET'])
def register_vehicle(person_id):
    r = requests.get("http://localhost:8086/api/person/list")
    data = r.json()
    session['person_id'] = person_id  
    return render_template('/vehiculo/guardar.html', lista=data["data"], person_id=person_id)