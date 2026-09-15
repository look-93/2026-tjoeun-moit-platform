from django.db import models


class MeetupCategoryStatistics(models.Model):
    category_name = models.CharField(max_length=100)
    meetup_count = models.IntegerField(default=0)
    applicant_count = models.IntegerField(default=0)
    average_application_rate = models.FloatField(default=0.0)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        db_table = "meetup_category_statistics"
        ordering = ["-meetup_count"]

    def __str__(self):
        return self.category_name