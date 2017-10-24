import { BaseEntity, User } from './../../shared';

export class Conversation implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public time?: any,
        public deleted?: boolean,
        public userOne?: User,
        public userTwo?: User,
    ) {
        this.deleted = false;
    }
}
