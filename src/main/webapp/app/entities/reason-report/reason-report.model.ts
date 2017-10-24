import { BaseEntity } from './../../shared';

export class ReasonReport implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public reason?: any,
        public report?: BaseEntity,
    ) {
    }
}
