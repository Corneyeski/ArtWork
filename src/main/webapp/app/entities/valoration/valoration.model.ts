import { BaseEntity, User } from './../../shared';

export class Valoration implements BaseEntity {
    constructor(
        public id?: number,
        public mark?: number,
        public time?: any,
        public user?: User,
        public multimedia?: BaseEntity,
    ) {
    }
}
