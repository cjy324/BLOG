/* wellcome 애니메이션 시작 */
const showWelcome = $( document ).ready( function() {
        var visTime = 600;
        $( '.span1' ).animate( {
          opacity: '1'
        }, visTime, function() {
          $( '.span2' ).animate( {
            opacity: '1'
          }, visTime, function() {
            $( '.span3' ).animate( {
              opacity: '1'
            }, visTime, function() {
            $( '.span4' ).animate( {
              opacity: '1'
            }, visTime, function() {
            $( '.span5' ).animate( {
              opacity: '1'
            }, visTime, function() {
            $( '.span6' ).animate( {
              opacity: '1'
            }, visTime, function() {
            $( '.span7' ).animate( {
              opacity: '1'
            }, visTime, function() {
            $( '.span8' ).animate( {
              opacity: '0'
            }, visTime, function() {
            $( '.span8' ).animate( {
              opacity: '1'
            }, visTime );
                   });
                  } );
                 } );
               } );
              } );
            } );
          } );
        } );
      } );

/* wellcome 애니메이션 끝 */