import os
from flask import Flask

def create_app():
    app = Flask(__name__, instance_relative_config=False)
    app.secret_key = os.getenv('SECRET_KEY', 'una_clave_secreta_unica_y_segura')
    with app.app_context():
        from routes.routePersona import routePersona
        from routes.routeVehiculo import routeVehiculo
        from routes.routeOrder import routeOrder
        from routes.routeServices import routeServices
        from routes.routeSesion import routeSesion
        app.register_blueprint(routePersona)
        app.register_blueprint(routeVehiculo)
        app.register_blueprint(routeOrder)
        app.register_blueprint(routeServices)
        app.register_blueprint(routeSesion)
    return app
