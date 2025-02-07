// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic'

import {AppModule} from './app/app.module'

platformBrowserDynamic()
    .bootstrapModule(AppModule)
    .catch(err => console.error(err))
