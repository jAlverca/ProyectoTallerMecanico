from flask import Blueprint, json, render_template, request, redirect, flash, jsonify
import requests

routePersona = Blueprint("routePersona", __name__)

@routePersona.route('/person/list', methods=['GET'])
def list_person(msg=''):
    r = requests.get("http://localhost:8086/api/person/list")
    print("Status Code:", r.status_code)
    if r.status_code == 200:
        try:
            data = r.json()
            return render_template('/persona/lista.html', lista=data["data"])
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
        return redirect('/person/list')
        
    else:
        flash('Error al guardar persona', category='error')
        return redirect('/person/list')
    
@routePersona.route('/person/edit/<int:idPersona>', methods=['GET', 'POST'])
def edit_person(idPersona):
    print(request.method)
    print(f"ID: {idPersona}")
    
    if request.method == 'GET':
        r = requests.get(f"http://localhost:8086/api/person/get/{idPersona}")
        data = r.json()
        print(data)
        return render_template('/persona/editar.html', person=data["data"])
    else:
        print("entro")
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataF = {
            "nombres": form['nombres'],
            "apellidos": form['apellidos'],
            "identificacion": form['identificacion'],
            "telefono": form['telefono'],
            "direccion": form['direccion']  # Asegúrate de incluir todos los campos necesarios
        }
        print("Datos enviados:", dataF)
        r = requests.put(f"http://localhost:8086/api/person/update/{idPersona}", data=json.dumps(dataF), headers=headers)
        print("Status Code:", r.status_code)
        dat = r.json()
        print("Respuesta de la API:", dat)
        if r.status_code == 200:
            print("persona actualizada")
            flash('Persona actualizada correctamente', category='info')
            return redirect('/person/list')
        else:
            print("Error al actualizar persona")
            flash('Error al actualizar persona', category='error')
            return redirect('/person/list')
        
@routePersona.route('/person/register')
def register():
    r = requests.get("http://localhost:8086/api/person/list")
    data = r.json()
    return render_template('/persona/guardar.html', lista = data["data"])
        
@routePersona.route('/person/delete/<int:idPersona>', methods=['POST'])
def delete_person(idPersona):
    headers = {'Content-Type': 'application/json'}
    r = requests.delete(f"http://localhost:8086/api/person/delete/{idPersona}", headers=headers)
    dat = r.json()
    if r.status_code == 200:
        flash('Persona eliminada correctamente', category='info')
        return redirect('/person/list')
    else:
        flash('Error al eliminar persona', category='error')
        return redirect('/person/list')
    
@routePersona.route('/person/search/', methods=['POST'])
def search_person():
    data = request.json
    atributo = data.get('searchAttr')
    valor = data.get('searchField')
    print(f"Atributo: {atributo}, Valor: {valor}")
    r = requests.get(f"http://localhost:8086/api/person/search/{atributo}/{valor}")
    data = r.json()
    print(f"Data: {data}")
    return jsonify(data["data"])




   
    
