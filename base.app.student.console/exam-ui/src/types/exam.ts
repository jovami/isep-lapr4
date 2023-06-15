export interface Question {
    id: number;
    description: string;

    groups?: { [key: string]: string[] };
    choices?: string[];

    phrase1?: string[];
    phrase2?: string[];

    singleAnswer?: boolean;
    options?: string[];
    type:
    | "MATCHING"
    | "MULTIPLE_CHOICE"
    | "SHORT_ANSWER"
    | "NUMERICAL"
    | "MISSING_WORDS"
    | "TRUE_FALSE";
};

export interface Section {
    id: number;
    description: string;
    questions: Question[];
};

export interface Exam {
    title: string;
    description: string;
    sections: Section[];
};
