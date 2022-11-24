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
        curr.execute(
            "SELECT * FROM experiments WHERE name='4_doors' and fire_pits=10 and fire_strength=" + str(p) + ";")
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
        avg.append(count / len(data) * 100 / 500)
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
        curr.execute("SELECT * FROM experiments WHERE name='4_doors' and fire_strength=5 and fire_pits=" + str(p) + ";")
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
        avg.append(count / len(data) * 100 / 500)
    fig, ax = plt.subplots()  # Create a figure containing a single axes.
    plt.title('Porcentaje de muertes segun cant. de focos de fuego')
    plt.ylabel('Muertes en %')
    plt.xlabel('Focos de fuego')
    plt.xticks(pits)
    ax.plot(pits, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
    plt.savefig("plot-pits.png")


def boxplot():
    experiments = ["4_doors", "2_doors", "4_doors_no_collisions"]
    avg = []
    boxplotData = []

    for experiment in experiments:
        for p in pits:
            curr.execute(
                f"SELECT * FROM experiments WHERE name='{experiment}' and fire_strength=5 and fire_pits=" + str(p) + ";")
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
        plt.title(f'Experimento: {experiment}')
        plt.ylabel("Muertes")
        plt.xlabel("Focos de fuego")
        ax.legend(['Promedio de muertes x experimento'], loc="upper left")
        ax.plot(avg, marker='.', linewidth=3, markersize=16)
        ax.set_xticklabels(pits)
        fig.savefig(f"boxplot_{experiment}.png")
        avg.clear()
        boxplotData.clear()

def boxplotByStrength():
    experiments = ["4_doors", "2_doors", "4_doors_no_collisions"]
    avg = []
    boxplotData = []

    for experiment in experiments:
        for s in strength:
            curr.execute(
                f"SELECT * FROM experiments WHERE name='{experiment}' and fire_strength={s} and fire_pits=10;")
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
        plt.title(f'Experimento: {experiment}')
        plt.ylabel("Muertes")
        plt.xlabel("Focos de fuego")
        ax.legend(['Promedio de muertes x experimento'], loc="upper left")
        ax.plot(avg, marker='.', linewidth=3, markersize=16)
        ax.set_xticklabels(strength_labels)
        fig.savefig(f"boxplot_{experiment}_strength.png")
        avg.clear()
        boxplotData.clear()

def timeToScape():
    experiments = ["2_doors", "4_doors", "4_doors_no_collisions"]
    avg = []
    for experiment in experiments:
        curr.execute(
            f"SELECT * FROM experiments WHERE name='{experiment}'")
        data = curr.fetchall()
        ticks = []
        for row in data:
            ticks.append(row[7])
        avg.append(np.mean(ticks))

    fig, ax = plt.subplots()
    ax.bar(experiments, avg, width=0.4, color=["green"])
    ax.set_ylabel('Ticks')
    ax.set_title('Tiempo de escape segun experimento')
    plt.savefig("ticks.png")


def compareExperiments():
    experiments = ["2_doors", "4_doors", "4_doors_no_collisions"]
    plotData = []
    for experiment in experiments:
        avg = []
        for p in pits:
            curr.execute(
                f"SELECT * FROM experiments WHERE name='{experiment}' and fire_strength=5 and fire_pits=" + str(p) + ";")
            data = curr.fetchall()
            allDeaths = []
            for row in data:
                allDeaths.append(row[5])
            avg.append(np.mean(allDeaths) * 100 / 500)
        plotData.append(avg)

    x = np.arange(len(pits))
    width = 0.25
    fig, ax = plt.subplots()
    ax.bar(x - width - 0.05, plotData[0], width, label=experiments[0])
    ax.bar(x + 0, plotData[1], width, label=experiments[1])
    ax.bar(x + width + 0.05, plotData[2], width, label=experiments[2])
    ax.set_ylabel('Muertes en %')
    ax.set_xlabel('Focos de fuego')
    ax.set_title('Comparación de % muertes por focos')
    ax.set_xticks(x, pits)
    ax.legend()
    fig.tight_layout()
    plt.savefig("compare_experiments.png")


def compareExperimentsByStrength():
    experiments = ["2_doors", "4_doors", "4_doors_no_collisions"]
    plotData = []
    for experiment in experiments:
        avg = []
        for s in strength:
            curr.execute(
                f"SELECT * FROM experiments WHERE name='{experiment}' and fire_strength={s} and fire_pits=10;")
            data = curr.fetchall()
            allDeaths = []
            for row in data:
                allDeaths.append(row[5])
            avg.append(np.mean(allDeaths) * 100 / 500)
        plotData.append(avg)

    x = np.arange(len(strength))
    width = 0.25  # the width of the bars
    fig, ax = plt.subplots()
    ax.bar(x - width - 0.05, plotData[0], width, label=experiments[0])
    ax.bar(x + 0, plotData[1], width, label=experiments[1])
    ax.bar(x + width + 0.05, plotData[2], width, label=experiments[2])
    ax.set_ylabel('Muertes en %')
    ax.set_xlabel('Potencia del fuego')
    ax.set_title('Comparación de % muertes por intensidad')
    ax.set_xticks(x, strength_labels)
    ax.legend()
    fig.tight_layout()
    plt.savefig("compare_experiment_strength.png")

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
    curr.execute("SELECT * FROM experiments WHERE name='4_doors' and fire_pits=10 and fire_strength=" + str(p) + ";")
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
    avg.append(count / len(data) * 100 / 500)

x = np.linspace(0, 100, 100)
for n in range(0, len(avgPerTick)):
    y = avgPerTick[n] * x * 100 / 500
    plt.plot(x, y, label='Potencia del fuego: ' + strength_labels[n])
plt.title('Evolucion de muertes segun la fuerza del fuego')
plt.ylabel('Muertes en %')
plt.xlabel('Ticks')
plt.legend()
plt.savefig("averages.png")

boxplot()
boxplotByStrength()
deathsByStrength()
deathsByPits()
compareExperiments()
compareExperimentsByStrength()
timeToScape()
