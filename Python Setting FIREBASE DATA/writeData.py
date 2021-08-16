def get_data(fb_app):
    result = fb_app.get('/', None)  # return None if realtime database is empty
    print(result)


def write_data(fb_app, list_of_data):
    result = [fb_app.post('/FXCMDATA', data=item, params={'print': 'pretty'}) for item in list_of_data]
    print(result)
