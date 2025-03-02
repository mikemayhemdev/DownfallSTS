package expansioncontent.cards;


import champ.ChampMod;
import champ.relics.PowerArmor;
import collector.cards.AbstractCollectorCard;
import collector.cards.OnPyreCard;
import collector.cards.SomberShield;
import collector.powers.AddCopyNextTurnPower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

import static expansioncontent.cards.AbstractExpansionCard.makeID;
import static expansioncontent.expansionContentMod.loadJokeCardImage;
import static hermit.util.Wiz.applyToSelfTop;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class SuperWhirlingFlame extends AbstractExpansionCard {
    public final static String ID = makeID(SuperWhirlingFlame.class.getSimpleName());
    // intellij stuff skill, self, common, , , 7, 3, ,

    public SuperWhirlingFlame() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        isMultiDamage = true;
        //todo attack bg instead of skill bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_collector.png", "expansioncontentResources/images/1024/bg_boss_collector.png");

        tags.add(expansionContentMod.STUDY_COLLECTOR);
        tags.add(expansionContentMod.STUDY);
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "SuperWhirlingFlame.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        atb(new DiscardPileToTopOfDeckAction(p));
    }

    public void upp() {
        upgradeDamage(3);
    }
}