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

avg = []
strength = [2, 5, 8, 11]

for p in strength:
    curr.execute("SELECT * FROM experiments WHERE fire_strength=" + str(p) + ";")
    data = curr.fetchall()
    count = 0
    for row in data:
        count += row[5]
    avg.append(count / len(data) * 100 / 200)

fig, ax = plt.subplots()  # Create a figure containing a single axes.
plt.ylabel('Deaths in %')
plt.xlabel('Fire-Strength')
plt.xticks(strength)
ax.plot(strength, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
plt.savefig("plot-strength.png")

avg = []
pits = [5, 10, 15, 20]

for p in pits:
    curr.execute("SELECT * FROM experiments WHERE fire_pits=" + str(p) + ";")
    data = curr.fetchall()
    count = 0
    for row in data:
        count += row[5]
    avg.append(count / len(data) * 100 / 200)

fig, ax = plt.subplots()  # Create a figure containing a single axes.
plt.ylabel('Deaths in %')
plt.xlabel('Fire-Pits')
plt.xticks(pits)
ax.plot(pits, avg, marker='.', linewidth=3, markersize=18)  # Plot some data on the axes.
plt.savefig("plot-pits.png")


x = np.array([5, 15, 25, 15, 45, 55]).reshape((-1, 1))
y = np.array([5, 20, 14, 132, 22, 38])
model = LinearRegression()
model.fit(x, y)

y_new = model.predict(x)

plt.figure(figsize=(4, 3))
ax = plt.axes()
ax.scatter(x, y)
ax.plot(x, y_new)

ax.set_xlabel('x')
ax.set_ylabel('y')

ax.axis('tight')

plt.savefig("plot-test2.png")


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

