package downfall.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.expansionContentMod;

import static expansioncontent.cards.AbstractExpansionCard.makeID;

public class BloodySacrifice extends AbstractDownfallCard {


    public static final String ID = makeID("BloodySacrifice");
    public static final String IMG_PATH = expansionContentMod.makeCardPath("Antidote.png");
    private static final CardStrings cardStrings;
    private float ratio = 0.15f;
    private int lose_hp;

    public BloodySacrifice() {
        super(ID, cardStrings.NAME, IMG_PATH, 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        isEthereal = true;
        exhaust = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        lose_hp = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * ratio);
        rawDescription = cardStrings.DESCRIPTION.replace("{amount}",Integer.toString(lose_hp));
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LoseHPAction(p, p, lose_hp));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(p, 1, true), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            ratio = 0.09f;
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
