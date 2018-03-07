/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CyberholocampusTestModule } from '../../../test.module';
import { NotificationDataDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/notification-data/notification-data-delete-dialog.component';
import { NotificationDataService } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.service';

describe('Component Tests', () => {

    describe('NotificationData Management Delete Component', () => {
        let comp: NotificationDataDeleteDialogComponent;
        let fixture: ComponentFixture<NotificationDataDeleteDialogComponent>;
        let service: NotificationDataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [NotificationDataDeleteDialogComponent],
                providers: [
                    NotificationDataService
                ]
            })
            .overrideTemplate(NotificationDataDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationDataDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationDataService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
