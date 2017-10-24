import { BaseEntity } from './../../shared';

export class Experience implements BaseEntity {
    constructor(
        public id?: number,
        public startYear?: number,
        public endingYear?: number,
        public name?: string,
        public company?: string,
        public competitions?: any,
    ) {
    }
}
