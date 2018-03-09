import { BaseEntity } from './../../shared';

export const enum NotificationType {
    'INFO',
    'ERROR',
    'CHECK'
}

export class Notification implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public type?: NotificationType,
        public title?: string,
        public active?: boolean,
        public infos?: BaseEntity[],
        public affectations?: BaseEntity[],
        public building?: BaseEntity,
    ) {
        this.active = false;
    }
}
