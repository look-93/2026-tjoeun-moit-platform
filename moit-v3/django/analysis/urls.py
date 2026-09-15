from django.urls import path
from . import views

urlpatterns = [
    #http://localhost:8000/dashboard/ 요청시 views.py의 dashboard_view 연결
    path("meetups/", views.meetup_statistics)
]