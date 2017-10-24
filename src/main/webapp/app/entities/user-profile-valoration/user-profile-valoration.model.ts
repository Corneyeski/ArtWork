import { BaseEntity, User } from './../../shared';

export class UserProfileValoration implements BaseEntity {
    constructor(
        public id?: number,
        public value?: number,
        public comments?: string,
        public valuator?: User,
        public valuated?: User,
    ) {
    }
}
