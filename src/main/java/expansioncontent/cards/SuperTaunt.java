package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;

import static champ.ChampMod.loadJokeCardImage;

public class SuperTaunt extends AbstractExpansionCard {

    public final static String ID = makeID("SuperTaunt");

    public SuperTaunt() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        //tags.add(ChampMod.OPENER);
        this.magicNumber = this.baseMagicNumber = 1;
        loadJokeCardImage(this, "SuperTaunt.png");
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_champ.png", "expansioncontentResources/images/1024/bg_boss_champ.png");

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            applyToEnemy(m, autoWeak(m, 1));
            applyToEnemy(m, autoVuln(m, 1));
        }
        else {
            AbstractDungeon.getMonsters().monsters.stream().filter(m2 -> !m2.isDead && !m2.isDying).forEach(m2 -> {
                applyToEnemy(m2, autoWeak(m2, 1));
                applyToEnemy(m2, autoVuln(m2, 1));
            });
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        target = CardTarget.ALL_ENEMY;
    }
}
