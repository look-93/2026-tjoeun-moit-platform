import { useEffect, useMemo, useState } from "react";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    ArcElement,
    Title,
    Tooltip,
    Legend,
} from "chart.js";
import { Bar, Doughnut } from "react-chartjs-2";
import { Card, Col, Row, Statistic, Spin, Empty } from "antd";
import axios from "../../../api/axios";

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    ArcElement,
    Title,
    Tooltip,
    Legend
);

export default function MeetupStatisticsPage() {
    const [statistics, setStatistics] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getMeetupStatistics();
    }, []);

    const getMeetupStatistics = async () => {
        try {
            const response = await axios.get(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/admin/statistics/meetups/result`
            );

            //console.log("Spring 통계 응답:", response.data);

            setStatistics(response.data || []);
        } catch (error) {
            //console.error("모임 통계 조회 실패:", error);
        } finally {
            setLoading(false);
        }
    };

    // =========================================================
    // 전체 통계
    // =========================================================

    const totalMeetupCount = useMemo(() => {
        return statistics.reduce(
            (total, item) => total + item.meetupCount,
            0
        );
    }, [statistics]);

    const totalApplicantCount = useMemo(() => {
        return statistics.reduce(
            (total, item) => total + item.applicantCount,
            0
        );
    }, [statistics]);

    const totalAverageApplicationRate = useMemo(() => {
        if (statistics.length === 0) {
            return 0;
        }

        const total = statistics.reduce(
            (sum, item) =>
                sum + item.averageApplicationRate,
            0
        );

        return Number(
            (total / statistics.length).toFixed(2)
        );
    }, [statistics]);

    // =========================================================
    // 카테고리
    // =========================================================

    const categories = statistics.map(
        (item) => item.categoryName
    );

    // =========================================================
    // 공통 차트 옵션
    // 모임 수 → 가로 막대
    // =========================================================

    const baseOptions = {
        responsive: true,
        maintainAspectRatio: false,

        indexAxis: "y",

        plugins: {
            legend: {
                display: false,
            },

            tooltip: {
                displayColors: false,

                callbacks: {
                    label: (context) => {
                        return `${context.raw}`;
                    },
                },
            },
        },

        scales: {
            x: {
                beginAtZero: true,

                grid: {
                    display: false,
                },

                border: {
                    display: false,
                },

                ticks: {
                    precision: 0,
                },
            },

            y: {
                grid: {
                    display: false,
                },

                border: {
                    display: false,
                },
            },
        },
    };

    // =========================================================
    // 1. 모임 수
    // 가로 막대그래프
    // =========================================================

    const meetupCountData = {
        labels: categories,

        datasets: [
            {
                label: "모임 수",

                data: statistics.map(
                    (item) => item.meetupCount
                ),

                backgroundColor: "rgba(24, 144, 255, 0.75)",
                hoverBackgroundColor: "rgba(24, 144, 255, 1)",

                borderRadius: 8,
                borderSkipped: false,
                barThickness: 22,
            },
        ],
    };

    // =========================================================
    // 2. 신청자 수
    // 도넛 차트
    // =========================================================

    const applicantCountData = {
        labels: categories,

        datasets: [
            {
                label: "신청자 수",

                data: statistics.map(
                    (item) => item.applicantCount
                ),

                backgroundColor: [
                    "rgba(24, 144, 255, 0.8)",
                    "rgba(82, 196, 26, 0.8)",
                    "rgba(250, 173, 20, 0.8)",
                    "rgba(245, 34, 45, 0.8)",
                    "rgba(114, 46, 209, 0.8)",
                    "rgba(19, 194, 194, 0.8)",
                    "rgba(235, 47, 150, 0.8)",
                    "rgba(250, 140, 22, 0.8)",
                ],

                borderColor: "#ffffff",
                borderWidth: 3,

                hoverOffset: 8,
            },
        ],
    };

    const applicantCountOptions = {
        responsive: true,
        maintainAspectRatio: false,

        cutout: "65%",

        plugins: {
            legend: {
                position: "right",

                labels: {
                    padding: 16,

                    usePointStyle: true,

                    pointStyle: "circle",
                },
            },

            tooltip: {
                callbacks: {
                    label: (context) => {
                        const value = context.raw;

                        return ` ${context.label}: ${value}명`;
                    },
                },
            },
        },
    };

    // =========================================================
    // 3. 평균 신청률
    // 세로 막대그래프
    // =========================================================

    const applicationRateData = {
        labels: categories,

        datasets: [
            {
                label: "평균 신청률",

                data: statistics.map(
                    (item) =>
                        item.averageApplicationRate
                ),

                backgroundColor: "rgba(114, 46, 209, 0.75)",
                hoverBackgroundColor: "rgba(114, 46, 209, 1)",

                borderRadius: 8,
                borderSkipped: false,
                barThickness: 22,
            },
        ],
    };

    const applicationRateOptions = {
        responsive: true,
        maintainAspectRatio: false,

        indexAxis: "x",

        plugins: {
            legend: {
                display: false,
            },

            tooltip: {
                displayColors: false,

                callbacks: {
                    label: (context) =>
                        `평균 신청률: ${context.raw}%`,
                },
            },
        },

        scales: {
            x: {
                grid: {
                    display: false,
                },

                border: {
                    display: false,
                },
            },

            y: {
                beginAtZero: true,

                min: 0,
                max: 100,

                grid: {
                    display: true,
                },

                border: {
                    display: false,
                },

                ticks: {
                    callback: (value) =>
                        `${value}%`,
                },
            },
        },
    };

    // =========================================================
    // 로딩
    // =========================================================

    if (loading) {
        return (
            <div
                style={{
                    minHeight: "400px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                }}
            >
                <Spin size="large" />
            </div>
        );
    }

    // =========================================================
    // 데이터 없음
    // =========================================================

    if (statistics.length === 0) {
        return (
            <Card>
                <Empty description="통계 데이터가 없습니다." />
            </Card>
        );
    }

    // =========================================================
    // 화면
    // =========================================================

    return (
        <div
            style={{
                padding: "32px",
                background: "#f7f8fa",
                minHeight: "100vh",
            }}
        >
            {/* ================================================= */}
            {/* 제목 */}
            {/* ================================================= */}

            <div
                style={{
                    marginBottom: "28px",
                }}
            >
                <h1
                    style={{
                        margin: 0,
                        fontSize: "28px",
                        fontWeight: 700,
                    }}
                >
                    모임 통계
                </h1>

                <p
                    style={{
                        marginTop: "8px",
                        color: "#8c8c8c",
                        fontSize: "14px",
                    }}
                >
                    카테고리별 모임 현황 및 신청률을 확인할 수 있습니다.
                </p>
            </div>

            {/* ================================================= */}
            {/* 요약 카드 */}
            {/* ================================================= */}

            <Row gutter={[20, 20]}>
                <Col xs={24} md={8}>
                    <Card
                        bordered={false}
                        style={{
                            borderRadius: "16px",
                            boxShadow:
                                "0 4px 16px rgba(0, 0, 0, 0.04)",
                        }}
                    >
                        <Statistic
                            title="전체 모임"
                            value={totalMeetupCount}
                            suffix="개"
                        />
                    </Card>
                </Col>

                <Col xs={24} md={8}>
                    <Card
                        bordered={false}
                        style={{
                            borderRadius: "16px",
                            boxShadow:
                                "0 4px 16px rgba(0, 0, 0, 0.04)",
                        }}
                    >
                        <Statistic
                            title="전체 신청자"
                            value={totalApplicantCount}
                            suffix="명"
                        />
                    </Card>
                </Col>

                <Col xs={24} md={8}>
                    <Card
                        bordered={false}
                        style={{
                            borderRadius: "16px",
                            boxShadow:
                                "0 4px 16px rgba(0, 0, 0, 0.04)",
                        }}
                    >
                        <Statistic
                            title="평균 신청률"
                            value={totalAverageApplicationRate}
                            suffix="%"
                            precision={2}
                        />
                    </Card>
                </Col>
            </Row>

            {/* ================================================= */}
            {/* 차트 */}
            {/* ================================================= */}

            <Row
                gutter={[20, 20]}
                style={{
                    marginTop: "20px",
                }}
            >
                {/* ================================================= */}
                {/* 모임 수 */}
                {/* ================================================= */}

                <Col xs={24} xl={12}>
                    <Card
                        bordered={false}
                        title="카테고리별 모임 수"
                        style={{
                            borderRadius: "16px",
                            boxShadow:
                                "0 4px 16px rgba(0, 0, 0, 0.04)",
                        }}
                    >
                        <div
                            style={{
                                height: "360px",
                            }}
                        >
                            <Bar
                                data={meetupCountData}
                                options={baseOptions}
                            />
                        </div>
                    </Card>
                </Col>

                {/* ================================================= */}
                {/* 신청자 수 - 도넛 */}
                {/* ================================================= */}

                <Col xs={24} xl={12}>
                    <Card
                        bordered={false}
                        title="카테고리별 신청자 수"
                        style={{
                            borderRadius: "16px",
                            boxShadow:
                                "0 4px 16px rgba(0, 0, 0, 0.04)",
                        }}
                    >
                        <div
                            style={{
                                height: "360px",
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                            }}
                        >
                            <Doughnut
                                data={applicantCountData}
                                options={applicantCountOptions}
                            />
                        </div>
                    </Card>
                </Col>

                {/* ================================================= */}
                {/* 평균 신청률 */}
                {/* ================================================= */}

                <Col xs={24}>
                    <Card
                        bordered={false}
                        title="카테고리별 평균 신청률"
                        style={{
                            borderRadius: "16px",
                            boxShadow:
                                "0 4px 16px rgba(0, 0, 0, 0.04)",
                        }}
                    >
                        <div
                            style={{
                                height: "400px",
                            }}
                        >
                            <Bar
                                data={applicationRateData}
                                options={applicationRateOptions}
                            />
                        </div>
                    </Card>
                </Col>
            </Row>
        </div>
    );
}