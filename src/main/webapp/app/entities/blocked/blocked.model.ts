import { BaseEntity, User } from './../../shared';

export class Blocked implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public block?: User,
        public blocked?: User,
    ) {
    }
}
