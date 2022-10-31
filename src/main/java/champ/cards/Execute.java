package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import sneckomod.SneckoMod;

public class Execute extends AbstractChampCard {

    public final static String ID = makeID("Execute");

    public Execute() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        baseCool = cool = 2;
        tags.add(ChampMod.FINISHER);
        tags.add(SneckoMod.BANNEDFORSNECKO);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.useJumpAnimation();
        atb(new VFXAction(new GoldenSlashEffect(m.hb.cX + 30.0F * Settings.scale, m.hb.cY, true), 0.1F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        atb(new VFXAction(new GoldenSlashEffect(m.hb.cX - 30.0F * Settings.scale, m.hb.cY, true), 0.1F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        finisher();
    }

    public void upp() {
        upgradeDamage(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}