import json

import pandas as pd

from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from .models import MeetupCategoryStatistics


@csrf_exempt
def meetup_statistics(request):

    # =========================================================
    # GET
    # =========================================================
    # Django DB에 저장된 통계 데이터 조회

    if request.method == "GET":

        statistics = MeetupCategoryStatistics.objects.all()

        result = []

        for statistic in statistics:

            result.append({
                "categoryName": statistic.category_name,
                "meetupCount": statistic.meetup_count,
                "applicantCount": statistic.applicant_count,
                "averageApplicationRate": statistic.average_application_rate
            })

        return JsonResponse({
            "statistics": result
        })


    # =========================================================
    # POST
    # =========================================================
    # Spring Boot에서 통계 데이터 수신

    if request.method == "POST":

        try:

            # =====================================================
            # Spring Boot에서 JSON 데이터 받기
            # =====================================================

            data = json.loads(request.body)

            statistics = data.get("statistics", [])


            # =====================================================
            # Pandas DataFrame 생성
            # =====================================================

            df = pd.DataFrame(statistics)

            print("====================================")
            print("Spring Boot 통계 데이터 수신")
            print("====================================")
            print(df)
            print("====================================")


            # =====================================================
            # Pandas 데이터 가공
            # =====================================================

            df = df[
                [
                    "categoryName",
                    "meetupCount",
                    "applicantCount",
                    "averageApplicationRate"
                ]
            ]


            # 결측값 처리

            df = df.fillna(0)


            # 숫자형 변환

            df["meetupCount"] = df["meetupCount"].astype(int)

            df["applicantCount"] = df["applicantCount"].astype(int)

            df["averageApplicationRate"] = (
                df["averageApplicationRate"].astype(float)
            )


            # 평균 신청률 소수점 둘째 자리

            df["averageApplicationRate"] = df[
                "averageApplicationRate"
            ].round(2)


            # =====================================================
            # 모임 수 기준 내림차순 정렬
            # =====================================================

            df = df.sort_values(
                by="meetupCount",
                ascending=False
            )


            # =====================================================
            # Pandas 분석 결과 DB 저장
            # =====================================================

            # 기존 통계 삭제

            MeetupCategoryStatistics.objects.all().delete()


            # 새로운 통계 저장

            for row in df.to_dict(orient="records"):

                MeetupCategoryStatistics.objects.create(
                    category_name=row["categoryName"],
                    meetup_count=row["meetupCount"],
                    applicant_count=row["applicantCount"],
                    average_application_rate=row[
                        "averageApplicationRate"
                    ]
                )


            print("====================================")
            print("통계 데이터 DB 저장 성공")
            print("====================================")


            # =====================================================
            # Pandas 분석 결과 출력
            # =====================================================

            print("====================================")
            print("Pandas 분석 결과")
            print("====================================")
            print(df)
            print("====================================")


            # =====================================================
            # DataFrame → JSON
            # =====================================================

            result = df.to_dict(
                orient="records"
            )


            # =====================================================
            # 응답
            # =====================================================

            return JsonResponse({
                "message": "통계 분석 및 저장 성공",
                "statistics": result
            })


        except Exception as e:

            print("====================================")
            print("통계 데이터 처리 실패")
            print("====================================")
            print(e)
            print("====================================")

            return JsonResponse({
                "message": "통계 데이터 처리 실패",
                "error": str(e)
            }, status=500)


    # =========================================================
    # 그 외 HTTP Method
    # =========================================================

    return JsonResponse({
        "message": "GET 또는 POST 요청만 허용됩니다."
    }, status=405)
