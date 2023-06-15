interface ExamResult {
    sections: ResultSection[];
    grade: number;
    maxGrade: number;
}

interface ResultSection {
    answers: Answer[];
};

interface Answer {
    points: number;
    maxPoints: number;
    feedback: string;
};

export type {
    ExamResult
}

