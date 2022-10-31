import psycopg2
import psycopg2.extras
import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression

pits = [5, 10, 15, 20]
strength_labels = ["Debil", "Regular", "Medio", "Fuerte"]
strength = [2, 5, 8, 11]


def deathsByStrength():
    avg = []
    for p in strength:
        curr.execute("SELECT * FROM experiments WHERE fire_strength=" + str(p) + ";")
        data = curr.fetchall()
        count = 0
        average = []
        for row in data:
            count += row[5]
            if row[5] > 0:
                average.append(1 / (row[7] / row[5]))
        sum = 0
        for n in average:
            sum += n
        avg.append(count / len(data) * 100 / 200)
    fig, ax = plt.subplots()  # Create a figure containing a single axes.
    plt.title('Porcentaje de muertes segun la fuerza del fuego')
    plt.ylabel('Muertes en %')
    plt.xlabel('Potencia del fuego')
    plt.xticks(strength)
    ax.plot(strength, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
    ax.set_xticklabels(strength_labels)
    plt.savefig("plot-strength.png")

def deathsByPits():
    avg = []
    for p in pits:
        curr.execute("SELECT * FROM experiments WHERE fire_pits=" + str(p) + ";")
        data = curr.fetchall()
        count = 0
        average = []
        for row in data:
            count += row[5]
            if row[5] > 0:
                average.append(1 / (row[7] / row[5]))
        sum = 0
        for n in average:
            sum += n
        avg.append(count / len(data) * 100 / 200)
    fig, ax = plt.subplots()  # Create a figure containing a single axes.
    plt.title('Porcentaje de muertes segun cant. de focos de fuego')
    plt.ylabel('Muertes en %')
    plt.xlabel('Focos de fuego')
    plt.xticks(strength)
    ax.plot(strength, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
    plt.savefig("plot-pits.png")


def boxplot():
    avg = []
    boxplotData = []
    for p in pits:
        curr.execute("SELECT * FROM experimentBoxPlot WHERE fire_pits=" + str(p) + ";")
        data = curr.fetchall()
        count = 0
        allDeaths = []
        for row in data:
            count += row[5]
            allDeaths.append(row[5])
        boxplotData.append(allDeaths)
        avg.append(count / len(data))

    fig, ax = plt.subplots()  # a figure with a single Axes

    ax.boxplot(boxplotData, 0, showfliers=False, positions=range(0, len(boxplotData)))
    plt.ylabel("Muertes")
    plt.xlabel("Focos de fuego")
    ax.plot(avg, marker='.', linewidth=3, markersize=16)
    ax.legend(['Promedio de muertes x experimento'], loc="upper left")
    ax.set_xticklabels(pits)

    fig.savefig("boxplot.png")


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

avgPerTick = []
avg = []

deaths = []

for p in strength:
    curr.execute("SELECT * FROM experiments WHERE fire_strength=" + str(p) + ";")
    data = curr.fetchall()
    count = 0
    average = []
    for row in data:
        count += row[5]
        if row[5] > 0:
            average.append(1 / (row[7] / row[5]))
    sum = 0
    for n in average:
        sum += n
    avgPerTick.append(sum / len(average))
    avg.append(count / len(data) * 100 / 200)

x = np.linspace(0, 100, 100)
for n in range(0, len(avgPerTick)):
    y = avgPerTick[n] * x * 100 / 200
    print(y)
    plt.plot(x, y, label='Potencia del fuego: ' + strength_labels[n])
plt.title('Evolucion de muertes segun la fuerza del fuego')
plt.ylabel('Muertes en %')
plt.xlabel('Ticks')
plt.legend()
plt.savefig("averages.png")

boxplot()
deathsByStrength()
deathsByPits()
