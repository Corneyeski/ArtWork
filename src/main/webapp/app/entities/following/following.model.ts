import { BaseEntity, User } from './../../shared';

export class Following implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public follower?: User,
        public followed?: User,
    ) {
    }
}
