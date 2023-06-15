interface RegularResolution {
    sections: RegularSectionAnswers[];
    submissionTime: Date;
    title: string;
};

interface RegularSectionAnswers {
    answers: string[];
};


export type {
    RegularResolution,
    RegularSectionAnswers,
}
