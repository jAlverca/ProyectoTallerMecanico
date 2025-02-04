from flask import Blueprint, json, render_template, request, redirect, flash, jsonify
import requests

routeServices = Blueprint("routeServices", __name__)

@routeServices.route('/services/list', methods=['GET'])
def list_services(msg=''):
    r = requests.get("http://localhost:8086/api/services/list")
    print("Status Code:", r.status_code)
    if r.status_code == 200:
        try:
            data = r.json()
            return render_template('/services/lista.html', lista=data["data"])
        except requests.exceptions.JSONDecodeError:
            print("Error: la respuesta no es un JSON válido.")
            return jsonify({"error": "Respuesta no es JSON válida"}), 500
    else:
        print("Error en la respuesta:", r.status_code)
        return jsonify({"error": "Error en la respuesta del servidor"}), 500


@routeServices.route('/services/save', methods=['POST'])
def save_services():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataF = { 
             "nombre":form["nombre"], 
             "descripcion":form["descripcion"],
             "pu":form["pu"], 
             "pt":form["pt"], 
             "codigo":form["codigo"]}
    r = requests.post("http://localhost:8086/api/services/save", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code == 200:
        print(dat)
        flash('Services guardada correctamente', category='info')
        return redirect('/services/list')
        
    else:
        print("Error al guardar services".r.json())
        flash('Error al guardar services', category='error')
        return redirect('/services/list')
    
@routeServices.route('/services/edit/<int:idServices>', methods=['GET', 'POST'])
def edit_services(idServices):
    print(request.method)
    print(f"ID: {idServices}")
    
    if request.method == 'GET':
        r = requests.get(f"http://localhost:8086/api/services/get/{idServices}")
        if r.status_code == 200:
            try:
                data = r.json()
                print(data)
                return render_template('/services/editar.html', services=data["data"])
            except requests.exceptions.JSONDecodeError:
                flash('Error al obtener datos de la services', category='error')
                return redirect('/services/lista.html')
        else:
            flash('Error al obtener datos de la services', category='error')
            return redirect('/services/lista.html')
    else:
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataF = {
            "apellidos": form["apellidos"], 
            "nombres": form["nombres"], 
            "identificacion": form["identificacion"], 
            "direccion": form["direccion"], 
            "telefono": form["telefono"]
        }
        r = requests.put(f"http://localhost:8086/api/services/update/{idServices}", data=json.dumps(dataF), headers=headers)
        if r.status_code == 200:
            try:
                dat = r.json()
                print(dat)
                flash('Services actualizada correctamente', category='info')
                return redirect('/services/lista.html')
            except requests.exceptions.JSONDecodeError:
                flash('Error al actualizar services', category='error')
                return redirect('/services/lista.html')
        else:
            print("Error al actualizar services")
            flash('Error al actualizar services', category='error')
            return redirect('/services/lista.html')
        
@routeServices.route('/services/register')
def register():
    r = requests.get("http://localhost:8086/api/services/list")
    data = r.json()
    return render_template('/services/guardar.html', lista = data["data"])
        



@routeServices.route('/services/delete/<int:idServicio>', methods=['POST'])
def delete_service(idServicio):
    
    print(f"ID a eliminar: {idServicio}")
    
    headers = {'Content-Type': 'application/json'}
    r = requests.delete(f"http://localhost:8086/api/services/delete/{idServicio}", headers=headers)
    dat = r.json()
    if r.status_code == 200:
        flash('Servicio eliminado correctamente', category='info')
        return redirect('/services/list')
    else:
        flash('Error al eliminar servicio', category='error')
        return redirect('/services/list')

    
