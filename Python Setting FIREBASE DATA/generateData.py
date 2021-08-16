from datetime import datetime as dt
import pandas as pd


def sort_today_data(orders_df):
    for index in range(len(orders_df)):
        if list(map(int, orders_df.loc[index, "date"].split("/"))) != list(
                map(int, dt.today().date().strftime('%m/%d/%Y').split("/"))):
            orders_df.drop([index], inplace=True)
    return orders_df


def open_csv_file(path):
    return pd.read_csv(path)


def data_per_row(row_data):
    limit, stop = str(abs(row_data["limit"])), str(abs(row_data["stop"]))
    if row_data["is_buy"]:
        limit, stop = stop, limit
    date_time_obj = dt.strptime(row_data["date"], '%m/%d/%Y').date().strftime('%d/%m/%Y')
    row_data_dict = {"Date": str(date_time_obj),
                     "Order": "H"+limit+"L"+stop,
                     "Is Buy": row_data["is_buy"]}
    return row_data_dict


