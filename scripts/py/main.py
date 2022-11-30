import psycopg2
import psycopg2.extras
from functions import *
import os

def get_connection():
    try:
        return psycopg2.connect("dbname=postgres user=postgres password=admin")
    except:
        return False


conn = get_connection()

if conn:
    print("Connection to the PostgreSQL established successfully.")
else:
    print("Connection to the PostgreSQL encountered and error.")

curr = conn.cursor()

if not os.path.isdir('plots'):
    os.makedirs('plots')
os.chdir('plots')

boxplot(curr)
boxplotByStrength(curr)
deathsByStrength(curr)
deathsByPits(curr)
compareExperiments(curr)
compareExperimentsByStrength(curr)
timeUntilScape(curr)
