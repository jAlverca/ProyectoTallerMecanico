from flask import Blueprint, json, render_template, request, redirect, flash, jsonify
import requests

routeSesion = Blueprint("routeSesion", __name__)
@routeSesion.route('/')
def session():
    return render_template('/sesion/index.html')


@routeSesion.route('/home')
def home():
    return render_template('home.html')

@routeSesion.route('/account/save', methods=['POST'])
def save_cuenta():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataF = {"correo":form["correo"], 
             "clave":form["clave"], 
             "idPersona":form["idPersona"],
    }
    r = requests.post("http://localhost:8086/api/account/save", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code == 200:
        flash('Cuenta guardada correctamente', category='info')
        return redirect('/account/register')
        
    else:
        flash('Error al guardar cuenta', category='error')
        return redirect('/account/register')

@routeSesion.route('/account/register')
def register():
    r = requests.get("http://localhost:8086/api/account/list")
    data = r.json()
    return render_template('/sesion/registro.html', lista = data["data"])