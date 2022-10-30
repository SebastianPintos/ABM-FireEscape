import psycopg2
import psycopg2.extras
import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression

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
strength = [2, 5, 8, 11]
deaths = []

for p in strength:
    curr.execute("SELECT * FROM experiments1234 WHERE fire_strength=" + str(p) + ";")
    data = curr.fetchall()
    count = 0
    average = []
    for row in data:
        count += row[5]
        if row[5] > 0:
            average.append(1/(row[7]/row[5]))
    sum = 0
    print(average)
    for n in average:
        sum += n
    avgPerTick.append(sum / len(average))
    avg.append(count / len(data) * 100 / 200)

x = np.linspace(0,100,100)
for n in range(0, len(avgPerTick)):
    y = avgPerTick[n] * x * 100 / 200
    plt.plot(x, y, label='Fire strength ' + str(strength[n]))
plt.ylabel('Deaths in %')
plt.xlabel('Ticks')
plt.legend()
plt.title('Deaths along time by fire strength')
plt.savefig("averages.png")

fig, ax = plt.subplots()  # Create a figure containing a single axes.
plt.ylabel('Deaths in %')
plt.xlabel('Fire-Strength')
plt.xticks(strength)
ax.plot(strength, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
plt.savefig("plot-strength.png")

avg = []
pits = [5, 10, 15]
boxplotData = []


for p in pits:
    curr.execute("SELECT * FROM experiments1234 WHERE fire_pits=" + str(p) + ";")
    data = curr.fetchall()
    count = 0
    allDeaths = []
    for row in data:
        count += row[5]
        allDeaths.append(row[5])
    boxplotData.append(allDeaths)
    avg.append(count / len(data) * 100 / 200)

fig, ax = plt.subplots()  # Create a figure containing a single axes.
plt.ylabel('Deaths in %')
plt.xlabel('Fire-Pits')
plt.xticks(pits)
ax.plot(pits, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
plt.savefig("plot-pits.png")


fig, ax = plt.subplots()  # a figure with a single Axes

ax.boxplot(boxplotData,0, showfliers=False, positions=range(0,len(boxplotData)))
ax.plot(avg, marker='.', linewidth=3, markersize=16)  # Plot some data on the axes.
ax.set_xticklabels(pits)

fig.savefig("boxplot.png")


#### Extraer todos los datos  de las columnas ####
# name = []
# fire_strength = []
# fire_pits = []
# alive = []
# death = []
# collisions = []
# ticks = []
#
# # PRINT THE RECORDS
# for row in data:
#     name.append(row[1])
#     fire_pits.append(row[2])
#     fire_strength.append(row[3])
#     alive.append(row[4])
#     death.append(row[5])
#     collisions.append(row[6])
#     ticks.append(row[7])

