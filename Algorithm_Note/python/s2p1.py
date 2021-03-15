a=float(input('请输入月利率：'))

res = 50000.0 / pow(1+a, 120)
 
print("平均成绩为：{}".format(res))
