/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MicroTestModule } from '../../../test.module';
import { InfoDefinitionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/info-definition/info-definition-delete-dialog.component';
import { InfoDefinitionService } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.service';

describe('Component Tests', () => {

    describe('InfoDefinition Management Delete Component', () => {
        let comp: InfoDefinitionDeleteDialogComponent;
        let fixture: ComponentFixture<InfoDefinitionDeleteDialogComponent>;
        let service: InfoDefinitionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [InfoDefinitionDeleteDialogComponent],
                providers: [
                    InfoDefinitionService
                ]
            })
            .overrideTemplate(InfoDefinitionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfoDefinitionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoDefinitionService);
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
