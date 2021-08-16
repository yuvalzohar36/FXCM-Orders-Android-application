from firebase import firebase
import generateData
import writeData

DATA_PATH = r"sorted_orders.csv"
FIRE_BASE_DATABASE_PATH = "https://fxcm-96d32-default-rtdb.firebaseio.com/.asia-southeast1.firebasedatabase.app/"
if __name__ == '__main__':
    data = generateData.open_csv_file(DATA_PATH)
    data = generateData.sort_today_data(data)
    data_in_list = data.apply(generateData.data_per_row, axis=1).tolist()
    fb_app = firebase.FirebaseApplication(FIRE_BASE_DATABASE_PATH, None)
    if len(data_in_list) > 0:
        writeData.write_data(fb_app, data_in_list)
    exit(0)