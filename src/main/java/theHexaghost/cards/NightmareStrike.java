package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NightmareStrike extends AbstractHexaCard {

    public final static String ID = makeID("NightmareStrike");

    public NightmareStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
        isEthereal = true;
        cardsToPreview = new ShadowStrike();
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void afterlife() {
        superFlash(Color.PURPLE);
        AbstractCard q = new ShadowStrike(this);
        if (upgraded) q.upgrade();
        atb(new MakeTempCardInHandAction(q));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            rawDescription = UPGRADE_DESCRIPTION;
            cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}