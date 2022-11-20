import matplotlib.pyplot as plt
import numpy as np
from matplotlib.patches import Polygon

# Creating dataset
np.random.seed(10)
data = [[90,60,40,10],[90,60,40,20]]

fig = plt.figure(figsize=(10, 7))

# Creating plot
plt.boxplot(data, 0, '')

# show plot
plt.show()