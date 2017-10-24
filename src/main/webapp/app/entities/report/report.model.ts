import { BaseEntity, User } from './../../shared';

export class Report implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public reason?: any,
        public reporter?: User,
        public reported?: User,
        public offer?: BaseEntity,
        public multimedia?: BaseEntity,
    ) {
    }
}
