/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MicroTestModule } from '../../../test.module';
import { InfoDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/info-definition/info-definition-detail.component';
import { InfoDefinitionService } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.service';
import { InfoDefinition } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.model';

describe('Component Tests', () => {

    describe('InfoDefinition Management Detail Component', () => {
        let comp: InfoDefinitionDetailComponent;
        let fixture: ComponentFixture<InfoDefinitionDetailComponent>;
        let service: InfoDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [InfoDefinitionDetailComponent],
                providers: [
                    InfoDefinitionService
                ]
            })
            .overrideTemplate(InfoDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfoDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoDefinitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InfoDefinition(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.infoDefinition).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
