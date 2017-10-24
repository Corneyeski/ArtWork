import { BaseEntity } from './../../shared';

export class Training implements BaseEntity {
    constructor(
        public id?: number,
        public startYear?: number,
        public endingYear?: number,
        public degree?: string,
        public studyCenter?: string,
        public competitionsContentType?: string,
        public competitions?: any,
    ) {
    }
}
