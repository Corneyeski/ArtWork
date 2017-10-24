import { BaseEntity } from './../../shared';

export class ResumeCreation implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public lastName?: string,
        public birthdate?: any,
        public photoContentType?: string,
        public photo?: any,
        public email?: string,
        public phone?: number,
        public address?: string,
        public skillsContentType?: string,
        public skills?: any,
        public tool?: BaseEntity,
        public language?: BaseEntity,
        public city?: BaseEntity,
        public training?: BaseEntity,
        public experience?: BaseEntity,
    ) {
    }
}
