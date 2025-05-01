package slimebound.slimes;

import automaton.AutomatonChar;
import automaton.AutomatonMod;
import automaton.AutomatonTextHelper;
import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.ForceShield;
import automaton.cards.FullRelease;
import automaton.cards.FunctionCard;
import automaton.powers.*;
import automaton.relics.ElectromagneticCoil;
import automaton.relics.OnCompileRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BobEffect;
import downfall.util.TextureLoader;

import java.util.HashMap;

public class SlimeHelper {
    static AbstractSlime Pike;
    static AbstractSlime Cid;

    public static int getPikeEnergy(){
        return Pike.getEnergy();
    }

    public static int getCidEnergy(){
        return Cid.getEnergy();
    }

    public static boolean doesPikeHaveEnchantment(enchantment enchantToCheck){
        return Pike.isEnchanted(enchantToCheck);
    }

    public static boolean doesCidHaveEnchantment(enchantment enchantToCheck){
        return Cid.isEnchanted(enchantToCheck);
    }

    //TODO - render / update probably lives here?
    //TODO - build in such a way that we might remove a slime. Could be a fun event.

    public enum enchantment {
        NOENEERGYCOST,
        DOUBLECOMMAND,
        CULTIST,
        CHAMP,
        TORCHHEAD,
        TIMEEATER,
        GUARDOIAN,
        HEXAGHOST;

        enchantment() {
        }
    }

    }

