package theHexaghost.cards.seals;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.cards.*;
import theHexaghost.powers.SealPostCombatPower;

import java.util.ArrayList;

public class FifthSeal extends AbstractSealCard {
    public final static String ID = makeID("FifthSeal");

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public FifthSeal() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);

        cardsList.add(new FirstSeal());
        cardsList.add(new SecondSeal());
        cardsList.add(new ThirdSeal());
        cardsList.add(new FourthSeal());
        cardsList.add(new SixthSeal());
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SealPostCombatPower(1));
    }



    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (cardsList.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = cardsList.get(previewIndex);
                }
                if (previewIndex == cardsList.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        cardsToPreview = null;
    }
}