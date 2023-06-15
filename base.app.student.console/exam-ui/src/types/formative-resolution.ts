interface FormativeResolution {
    sectionAnswers: FormativeSectionAnswers[];
};

interface FormativeSectionAnswers {
    answers: GivenAnswer[];
};

interface GivenAnswer {
    answer: string;
    questionID: number;
};


export type {
    FormativeResolution,
    FormativeSectionAnswers,
}
