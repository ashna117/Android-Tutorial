package com.abdullahalhasan.projectcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Home extends AppCompatActivity {

    TextView textView;
    TextView signView;
    float numberOne = 0f;
    float numberTwo = 0f;
    boolean plusBtn = false;
    boolean minusBtn = false;
    boolean multiBtn = false;
    boolean divBtn = false;
    boolean isNumberPress = true;
    boolean equalBtnPress = true;
    boolean programCounter = true;
    boolean dotBtnPress = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = (TextView) findViewById(R.id.textView);
        signView = (TextView) findViewById(R.id.signView);

    }


    public void one(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("1");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "1");
        }
    }

    public void two(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress || dotBtnPress!=true) {
            textView.setText("2");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "2");
        }
    }

    public void three(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("3");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "3");
        }
    }

    public void four(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("4");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "4");
        }
    }

    public void five(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("5");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "5");
        }
    }

    public void six(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("6");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "6");
        }
    }

    public void seven(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("7");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "7");
        }
    }

    public void eight(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("8");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "8");
        }
    }

    public void nine(View view) {
        programCounter = false;
        isNumberPress = false;
        if (equalBtnPress && dotBtnPress!=true) {
            textView.setText("9");
            equalBtnPress = false;
        } else {
            textView.setText(textView.getText() + "9");
        }
    }

    public void zero(View view) {
        programCounter = false;
        if (isNumberPress != true || dotBtnPress) {
            textView.setText(textView.getText() + "0");
            programCounter = false;
        } else {
            textView.setText("0");
            programCounter = false;
        }
    }

    public void plus(View view) {
        isNumberPress = true;
        dotBtnPress = false;
        if(plusBtn) {
            textView.getText();
        } /*else if(plusBtn!=true) {
            textView.getText();
        }*/ else {
            numberOne = Float.parseFloat(textView.getText().toString());
            signView.setText("+");
            textView.setText("");
            plusBtn = true;
        }

    }

    public void minus(View view) {
        isNumberPress = true;
        dotBtnPress = false;
        if(minusBtn) {
            textView.getText();
        } /*else if(plusBtn!=true) {
            textView.getText();
        }*/ else {
            numberOne = Float.parseFloat(textView.getText().toString());
            signView.setText("-");
            textView.setText("");
            minusBtn = true;
        }

    }

    public void multi(View view) {
        isNumberPress = true;
        dotBtnPress = false;
        if(multiBtn) {
            textView.getText();
        } /*else if(plusBtn!=true) {
            textView.getText();
        }*/ else {
            numberOne = Float.parseFloat(textView.getText().toString());
            signView.setText("*");
            textView.setText("");
            multiBtn = true;
        }
    }

    public void div(View view) {
        isNumberPress = true;
        dotBtnPress = false;
        if(divBtn) {
            textView.getText();
        } /*else if(plusBtn!=true) {
            textView.getText();
        }*/ else {
            numberOne = Float.parseFloat(textView.getText().toString());
            signView.setText("/");
            textView.setText("");
            divBtn = true;
        }
    }

    public void dot(View view) {
        if (dotBtnPress) {
            textView.setText(textView.getText() + "");
        } /*else if(programCounter) {
            textView.setText("0.");
            programCounter=false;
            dotBtnPress = true;
         }*/
         else {
            textView.setText(textView.getText() + ".");
            dotBtnPress = true;
        }
    }

    public void equal(View view) {
        programCounter = true;
        equalBtnPress = true;
        signView.setText("=");
        isNumberPress = true;
        dotBtnPress = false;
        numberTwo = Float.parseFloat(textView.getText().toString());

        if (plusBtn) {
            float result = numberOne + numberTwo;
            if (result % 1 == 0) {
                long tempData = Math.round(result);
                textView.setText(String.valueOf(tempData));
            }
            else {
                textView.setText(String.valueOf(result));
            }
            plusBtn = false;
        } else if (minusBtn) {
            float result = numberOne - numberTwo;
            if (result % 1 == 0) {
                long tempData = Math.round(result);
                textView.setText(String.valueOf(tempData));
            }
            else {
                textView.setText(String.valueOf(result));
            }

            minusBtn = false;
        }
        if (multiBtn) {
            float result = numberOne * numberTwo;
            if (result % 1 == 0) {
                long tempData = Math.round(result);
                textView.setText(String.valueOf(tempData));
            }
            else {
                textView.setText(String.valueOf(result));
            }
            multiBtn = false;
        }
        if (divBtn) {
            float result = numberOne / numberTwo;
            if (result % 1 == 0) {
                long tempData = Math.round(result);
                textView.setText(String.valueOf(tempData));
            }
            else {
                textView.setText(String.valueOf(result));
            }
            divBtn = false;
        }
    }

    public void clear(View view) {
        signView.setText("");
        textView.setText("0");
        numberOne = 0;
        numberTwo = 0;
        plusBtn = false;
        minusBtn = false;
        multiBtn = false;
        divBtn = false;
        isNumberPress = true;
        equalBtnPress = true;
        dotBtnPress = false;
        programCounter = true;
    }

}
