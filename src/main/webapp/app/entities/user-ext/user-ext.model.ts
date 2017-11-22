import { BaseEntity, User } from './../../shared';

export const enum Theme {
    'BLACK',
    'WHITE',
    'MINIMAL'
}

export class UserExt implements BaseEntity {
    constructor(
        public id?: number,
        public birthdate?: any,
        public imageContentType?: string,
        public image?: any,
        public phone?: number,
        public kind?: number,
        public tags?: string,
        public address?: string,
        public popular?: number,
        public companyPoints?: number,
        public validated?: boolean,
        public age?: number,
        public working?: boolean,
        public theme?: Theme,
        public resumeContentType?: string,
        public resume?: any,
        public user?: User,
        public city?: BaseEntity,
        public language?: BaseEntity,
        public profession?: BaseEntity,
        public workingOn?: User,
        public offers?: BaseEntity[],
        public tools?: BaseEntity[],
    ) {
        this.validated = false;
        this.working = false;
    }
}
