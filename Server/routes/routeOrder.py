from flask import Blueprint, json, render_template, request, redirect, flash, jsonify, session
import requests
import json
from flask_weasyprint import HTML, render_pdf

routeOrder = Blueprint("routeOrder", __name__)

@routeOrder.route('/order/list', methods=['GET'])
def list_person(msg=''):
    r = requests.get("http://localhost:8086/api/order/listAll")
    print("Status Code:", r.status_code)
    if r.status_code == 200:
        try:
            data = r.json()
            print(data)
            return render_template('/order/lista.html', lista=data["data"])
        except requests.exceptions.JSONDecodeError:
            print("Error: la respuesta no es un JSON válido.")
            return jsonify({"error": "Respuesta no es JSON válida"}), 500
    else:
        print("Error en la respuesta:", r.status_code)
        return jsonify({"error": "Error en la respuesta del servidor"}), 500

@routeOrder.route('/order/view/<id>')
def print_pdf(id):
    r = requests.get(f"http://localhost:8086/api/order/getAll/" + str(id))
    data = r.json()
    if data["msg"] == "OK":
        html = render_template('/order/pdf.html', data=data["data"])
        return render_pdf(HTML(string=html))
    else:
        return "Error al obtener los datos de la orden", 500
    
@routeOrder.route('/order/save', methods=['POST'])
def save_order():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    descripcion = form["txtdescripcion"][:-1]
    lista = descripcion.split(":")
    description = []
    for item in lista:
        lista_aux = item.split(",")
        if len(lista_aux) == 4: 
            try:
                services = lista_aux[0]
                cant =int(lista_aux[1])
                pu = float(lista_aux[2])
                pt = cant * pu
                description.append({"service": services, "cant": cant, "pu": pu, "pt": pt})
            except ValueError:
                print(f"Error al convertir valores: {lista_aux}")
                flash('Error al procesar la descripción', category='error')
                return redirect('/order/list')
        else:
            print(f"Formato incorrecto en la descripción: {item}")
            flash('Formato incorrecto en la descripción', category='error')
            return redirect('/order/list')
    
    dataF = {
        "vehiculo": form["vehiculo"],
        "iva": form["iva"][1:],  
        "total": form["total"][1:],  
        "subtotal": form["subtotal"][1:], 
        "descripcion": description
    }
    data = json.dumps(dataF)
    print(data)
    r = requests.post("http://localhost:8086/api/order/save", data=data, headers=headers)
    print("Status Code:", r.status_code)
    data = r.json()
    print(data)
    if r.status_code == 200:
        flash('Orden guardada correctamente', category='info')
        return redirect('/order/list')
    else:
        flash('Error al guardar la orden', category='error')
        return redirect('/order/register')


@routeOrder.route('/order/register')
def register_order():
    r = requests.get("http://localhost:8086/api/order/list")
    data = r.json()
    return render_template('/order/guardar.html', lista = data["data"])

