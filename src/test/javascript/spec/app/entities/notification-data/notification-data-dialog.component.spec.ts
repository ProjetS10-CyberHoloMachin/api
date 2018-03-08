/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CyberholocampusTestModule } from '../../../test.module';
import { NotificationDataDialogComponent } from '../../../../../../main/webapp/app/entities/notification-data/notification-data-dialog.component';
import { NotificationDataService } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.service';
import { NotificationData } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.model';
import { NotificationService } from '../../../../../../main/webapp/app/entities/notification';

describe('Component Tests', () => {

    describe('NotificationData Management Dialog Component', () => {
        let comp: NotificationDataDialogComponent;
        let fixture: ComponentFixture<NotificationDataDialogComponent>;
        let service: NotificationDataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [NotificationDataDialogComponent],
                providers: [
                    NotificationService,
                    NotificationDataService
                ]
            })
            .overrideTemplate(NotificationDataDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationDataDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationDataService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NotificationData(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.notificationData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'notificationDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NotificationData();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.notificationData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'notificationDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
