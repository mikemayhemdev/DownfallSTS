package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static awakenedOne.AwakenedOneMod.*;

public class AphoticShield extends AbstractSpellCard {
    public final static String ID = makeID(AphoticShield.class.getSimpleName());
    // intellij stuff skill, self, , , , , 2, 1

    public AphoticShield() {
        super(ID, 1, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        this.setBackgroundTexture("awakenedResources/images/512/bg_skill_awakened.png", "awakenedResources/images/1024/bg_skill_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(AphoticShield.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, magicNumber), magicNumber));
        }
        if (upgraded) {
            this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));
        }
    }

    public void upp() {
        //upgradeMagicNumber(1);
    }
}