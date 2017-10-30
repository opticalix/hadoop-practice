# -*- coding: utf-8 -*-
"""
Created on Mon Oct 30 23:11:38 2017

@author: optic
"""

import numpy as np;
import pandas as pd;
import re;

#def dateparser(x):
#    if (x.startswith('20')):
#        return pd.datetime.strptime(x, '%Y/%m/%d')
#    else:
#        return pd.datetime.strptime(x, '%m/%d/%Y')
    
#crime_data = pd.read_csv("data/Crime_Data_from_2014_to_Present.csv", parse_dates=['Date Occurred'], date_parser=dateparser)
crime_data = pd.read_csv("data/Crime_Data_from_2014_to_Present.csv")
#print(crime_data.head())
date_col = crime_data['Date Occurred']
#print(date_col.head())
#yyyymmdd = [d for d in date_col if not len(d) == 10]
# 自定义parser还是默认读csv后自己根据长度补充上20，再或者直接取长度为10的
# TODO 用mask抽取len为10的row，保存成新文件