from flask import Blueprint, json, render_template, request, redirect, flash
from flask import jsonify  # para el debug, opcional
import requests

routePersona = Blueprint("routePersona", __name__)


@routePersona.route('/', methods=['GET'])
def list_person(msg=''):
    r = requests.get("http://localhost:8086/api/person/list")
    print("Status Code:", r.status_code)
    if r.status_code == 200:
        try:
            data = r.json()
            return render_template('index.html', lista=data["data"])
        except requests.exceptions.JSONDecodeError:
            print("Error: la respuesta no es un JSON válido.")
            return jsonify({"error": "Respuesta no es JSON válida"}), 500
    else:
        print("Error en la respuesta:", r.status_code)
        return jsonify({"error": "Error en la respuesta del servidor"}), 500


@routePersona.route('/person/save', methods=['POST'])
def save_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataF = {"tipo":form["tipo"], 
             "apellidos":form["apellidos"], 
             "nombres":form["nombres"], 
             "identificacion":form["identificacion"], 
             "direccion":form["direccion"], 
             "telefono":form["telefono"]}
    r = requests.post("http://localhost:8086/api/person/save", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code == 200:
        flash('Persona guardada correctamente', category='info')
        return redirect('/')
        
    else:
        flash('Error al guardar persona', category='error')
        return redirect('/')
    
@routePersona.route('/person/register')
def register():
    r = requests.get("http://localhost:8086/api/person/list")
    data = r.json()
    return render_template('/persona/guardar.html', lista = data["data"])

@routePersona.route('/person/edit/<int:id>', methods=['GET', 'POST'])
def edit_person(id):
    print(f"ID: {id}")
    
    if request.method == 'GET':
        r = requests.get(f"http://localhost:8086/api/person/get/{id}")
        data = r.json()
        print(data)
        return render_template('/persona/editar.html', person = data["data"])
    else:
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataF = {
             "apellidos":form["apellidos"], 
             "nombres":form["nombres"], 
             "identificacion":form["identificacion"], 
             "direccion":form["direccion"], 
             "telefono":form["telefono"]}
        r = requests.put(f"http://localhost:8086/api/person/update/{id}", data=json.dumps(dataF), headers=headers)
        dat = r.json()
        print(dat)
        if r.status_code == 200:
            flash('Persona actualizada correctamente', category='info')
            return redirect('/')
        else:
            flash('Error al actualizar persona', category='error')
            return redirect('/')
        
@routePersona.route('/person/delete/<int:id>', methods=['POST'])
def delete_person(id):
    headers = {'Content-Type': 'application/json'}
    r = requests.delete(f"http://localhost:8086/api/person/delete/{id}", headers=headers)
    dat = r.json()
    if r.status_code == 200:
        return redirect('/')
    

@routePersona.route('/person/sort', methods=['POST'])
def sort_person():
    data = request.json
    method = data.get('sortMethod')
    atributo = data.get('sortField')
    orden = data.get('sortOrder')
    r = requests.get(f"http://localhost:8086/api/person/sort/{method}/{atributo}/{orden}")
    data = r.json()
    return jsonify(data["data"])


@routePersona.route('/person/search', methods=['POST'])
def search_person():
    data = request.json
    atributo = data.get('searchAttr')
    valor = data.get('searchField')
    print(f"Atributo: {atributo}, Valor: {valor}")
    r = requests.get(f"http://localhost:8086/api/person/search/{atributo}/{valor}")
    data = r.json()
    print(f"Data: {data}")
    return jsonify(data["data"])