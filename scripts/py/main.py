import psycopg2
import psycopg2.extras
import matplotlib.pyplot as plt


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
pits = [2, 5, 8, 11]

for p in pits:
    curr.execute("SELECT * FROM experiments WHERE fire_strength=" + str(p) + ";")
    data = curr.fetchall()
    count = 0
    for row in data:
        count += row[5]
    avg.append(count / len(data))

conn.close()
fig, ax = plt.subplots()  # Create a figure containing a single axes.
ax.plot(pits, avg)  # Plot some data on the axes.
plt.savefig("plot.png")
plt.show()


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

