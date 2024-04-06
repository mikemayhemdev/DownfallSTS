package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class NightmareStrike extends AbstractHexaCard {

    public final static String ID = makeID("NightmareStrike");

    public NightmareStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        isEthereal = true;
        cardsToPreview = new ShadowStrike();
        tags.add(CardTags.STRIKE);
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "NightmareStrike.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        superFlash(Color.PURPLE);
        AbstractCard q = new ShadowStrike(this);
        atb(new MakeTempCardInHandAction(q));
    }

    @Override
    public void afterlife() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m == null) return;
        this.calculateCardDamage(m);
        if(AbstractDungeon.player.hasPower("Pen Nib") ){
            this.damage /= 2;
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            this.damage *= 2;
        }else {
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}